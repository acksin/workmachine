(ns workmachine.core
  (:use compojure.core)
  (:use clj-html.core)
  (:use ring.middleware.json-params)
  (:require [compojure.route :as route]
            [compojure.handler :as handler]
            [clj-json.core :as json]
            [workmachine.workflow :as workflow]
            [workmachine.jobs :as jobs]
            [workmachine.work :as work]
            [workmachine.data-input :as input-types]
            [workmachine.data-output :as output-types]))

(defn- run-workflow [program jobs]
  (println ((first jobs) "topic_instruction"))
  (doseq [job jobs] (workflow/start-workflow (read-string program) job))
  "ok")

(defn- results []
  '(results))

(defroutes main-routes
  (GET "/" [] (str (jobs/number-of-available-jobs)))

  (GET "/jobs/assigned" [] (prn @jobs/assigned-jobs))
  (GET "/jobs/available" [] (prn @jobs/available-jobs))
  (GET "/jobs/finished" [] (fn [x]
                             (println @jobs/finished-jobs)
                             (str @jobs/finished-jobs)))
  
  (GET "/worker/:worker-id/assign" [worker-id] (work/assign worker-id))
  (POST "/worker/:worker-id/submit" {params :params}
        (work/submit
         (params :worker-id)
         {"topic" "icanhascheezburger"}))
         ;(dissoc params :worker-id)))
  (GET "/worker/:worker-id/unassign" [worker-id] (work/unassign worker-id))
  
  (GET "/mturk/assign" [] (work/assign "1")) ;; Obviously this should not be 1.
  (POST "/mturk/submit" [] (work/assign "1")) ;; Obviously this should not be 1.
  (POST "/mturk/unassign" [] (work/assign "1")) ;; Obviously this should not be 1.  

  ;; :program => '(pass in the workflow that the user wants to run.
  ;; :jobs => [{"name_tag" => "foo"}, {"name_tag" => "bar"}]
  (PUT "/run" [program jobs] (run-workflow program jobs))
  (GET "/results" [] (results))
  (route/resources "/")
  (route/not-found "<h1>Page not found</h1>"))

(def app (-> main-routes wrap-json-params))
