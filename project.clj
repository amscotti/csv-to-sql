(defproject csv-to-sql "0.1.0-SNAPSHOT"
  :description "Turn a CSV file into a SQL file"
  :url "https://github.com/amscotti/csv-to-sql"
  :license {:name "MIT License"
            :url "https://mit-license.org"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [org.clojure/data.csv "1.0.0"]
                 [org.clojure/tools.cli "1.0.206"]
                 [com.github.seancorfield/honeysql "2.1.818"]]
  :main ^:skip-aot csv-to-sql.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
