(ns workengine.core
  (:use compojure.core)
  (:use clj-html.core)
  (:require [compojure.route :as route]
            [compojure.handler :as handler]
            [resque-clojure.core :as resque]
            [workengine.workflow :as workflow]
            [workengine.jobs :as jobs]
            [workengine.work :as work]
            [workengine.data-input :as input-types]
            [workengine.data-output :as output-types]
            [workengine.blueprints.content :as content-workflow]))

(def resque-queues {:job "work-queue"})


(defn run-workflow []
  (str (content-workflow/workflow [{"topic" "http://google.com"}
                              {"topic" "http://yahoo.com"}
                              {"topic" "http://amazon.com"}])))

(defn assign-view [worker-id]
  (let [worker-job (work/assign worker-id)]
    (if worker-job
      (worker-job :html)
      (html [:div "No work"]))))

(defn unassign-view [worker-id]
  (work/unassign worker-id)
  (html [:div "Unassigned"]))

(defroutes main-routes
  (GET "/" [] (str (jobs/number-of-available-jobs)))
  (GET "/assignments" [] (str @jobs/assigned-jobs))
  (GET "/available" [] (str @jobs/available-jobs))
  (GET "/workflow" [] (run-workflow))
  (GET "/assign/:worker-id" [worker-id] (assign-view worker-id))
  (GET "/unassign/:worker-id" [worker-id] (unassign-view worker-id))
  (route/not-found "<h1>Page not found</h1>"))

(resque/start [(resque-queues :work)])

(def app (handler/site main-routes))
