(ns api.user.create
  (:require [api.db.db-util :as db]
            [clojure.data.json :as json]
            [clojurewerkz.neocons.rest.cypher :as cy]))


(defn parse-payload
  [req]
  (json/read-str
    (slurp
      (:body req))))

;; Create user node
(defn make-user-node
  [data]
  (let [user-data {:email (get data "email") :name (get data "name")}]
    (cy/tquery db/conn
               "CREATE (u:User {name: {name}, email: {email}})
                RETURN ID(u) as id"
               user-data)))

;; Creates user node and writes node id back
(defn handle-new-user
  [req]
  ;; TODO VALIDATE
  (let [body (parse-payload req)
        user-node (make-user-node body)]
    ;; Write back user ID
    (str (get (first user-node) "id"))))
