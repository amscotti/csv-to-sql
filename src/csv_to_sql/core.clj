(ns csv-to-sql.core
  (:require [clojure.tools.cli :refer [parse-opts]]
            [clojure.string :as string]
            [csv-to-sql.process :as process])
  (:gen-class))

(def cli-options
  [["-i" "--input INPUT" "Input CSV file path"]
   ["-o" "--output OUTPUT" "Output SQL file path"
    :default "output.sql"]
   ["-t" "--table TABLE" "Name of table to insert into"]
   [nil "--headers HEADER" "CSV headers"
    :parse-fn #(filter not-empty (string/split % #","))
    :validate [not-empty "Headers need to be comma separated list"]
    :default []]
   [nil "--skip-first" "Skip the first row"
    :default false]
   ["-d" "--dialect DIALECT" "SQL dialect"
    :default :ansi
    :parse-fn keyword
    :validate [#{:ansi :mysql :oracle :sqlserver} #(str % "is an invidate dialect")]]
   ["-h" "--help"]])

(defn- error-msg [errors]
  (str "The following errors occurred while parsing your command:\n\n"
       (string/join \newline errors)))

(defn- validate-args [args]
  (let [{:keys [options errors summary]} (parse-opts args cli-options)]
    (cond
      (:help options) {:exit-message summary :ok? true}
      errors {:exit-message (error-msg errors)}
      (not (:table options)) {:exit-message (error-msg ["--table must be specified"])}
      :else {:options options})))

(defn- exit [status msg]
  (println msg)
  (System/exit status))

(defn -main [& args]
  (let [{:keys [options exit-message ok?]} (validate-args args)]
    (if exit-message
      (exit (if ok? 0 1) exit-message)
      (process/process options))))
