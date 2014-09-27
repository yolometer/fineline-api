(ns api.user.create
  (:require [api.db.db-util :as db]
            [clojure.data.json :as json]
            [clojurewerkz.neocons.rest.nodes :as nn]
            [clojurewerkz.neocons.rest.labels :as nl]))


(defn parse-payload
  [req]
  (json/read-str
    (slurp
      (:body req))))

;; Create user node
(defn make-user-node
  [data]
  (let [node (nn/create
                db/conn
                {:email (get data "email")
                :name (get data "name")})]
    (nl/add db/conn node "User")))

;; Creates user node and writes node id back
(defn handle-new-user
  [req]
  ;; TODO VALIDATE
  (let [body (parse-payload req)
        user-node (make-user-node body)]
    (str (:id user-node))))
