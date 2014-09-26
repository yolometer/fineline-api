(ns api.project.project)

; Parse shit from req
(defn load-project-from-req
  [req]
  (let [id (-> req :route-params :id)]
    (str {:_id id})))

; Get all project data that has been updated since a specific time
(defn get-project-since
  [timestamp]
  timestamp)
