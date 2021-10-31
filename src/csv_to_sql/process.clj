(ns csv-to-sql.process
  (:require [clojure.data.csv :as csv]
            [clojure.java.io :as io]
            [honey.sql :as sql]))


(defn- insert-into [data table]
  {:insert-into table
   :values data})

(defn- csv-data->maps
  ([csv-data] (csv-data->maps csv-data [] false))
  ([csv-data headers] (csv-data->maps csv-data headers true))
  ([csv-data headers skip-first]
   (let [h (if (empty? headers) (first csv-data) headers)
         skip-first (or skip-first (not headers))]
     (map zipmap
          (->> h
               (map keyword)
               repeat)
          (if skip-first
            (rest csv-data)
            csv-data)))))

(defn- load-csv [filename]
  (with-open [reader (io/reader filename)]
    (doall
     (csv/read-csv reader))))

(defn- write-sql-file [sql file-path]
  (with-open [writer (io/writer file-path)]
    (.write writer sql)))

(defn process [{:keys [input output table headers skip-first dialect]}]
  (-> input
      load-csv
      (csv-data->maps headers skip-first)
      (insert-into table)
      (sql/format {:pretty true :inline true :dialect dialect})
      (first)
      (write-sql-file output)))