(ns api.project.read
  (:require [clojure.data.json :as json]))

(defn req-id [req] (-> req :route-params :id))

; Parse shit from req
(defn load-project-from-req
  [req]
  (str {:_id (req-id req)}))

; Get all project data that has been updated since a specific time
(defn get-project-since
  [timestamp]
  timestamp)
