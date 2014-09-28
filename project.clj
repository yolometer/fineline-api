(defproject api "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/data.json "0.2.5"]
                 [org.clojure/clojure "1.6.0"]
                 [compojure "1.1.8"]
                 [clojurewerkz/neocons "3.0.0"]
                 [org.clojure/tools.namespace "0.2.7"]]
  :plugins [[lein-ring "0.8.11"]]
  :ring {:handler api.handler/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring-mock "0.1.5"]]}})
