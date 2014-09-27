(ns api.user.create
  (:require [clojure.data.json :as json]
            [clojurewerkz.neocons.rest.nodes :as nn]))

(defn parse-payload
  [req]
  (json/read-str
    (slurp
      (:body req))))

(defn handle-new-user
  [req]
  (let [body (parse-payload req)]
    (str body)))
