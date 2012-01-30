(ns workmachine.core
  (:use compojure.core)
  (:use clj-html.core)
  (:require [compojure.route :as route]
            [compojure.handler :as handler]
            [resque-clojure.core :as resque]
            [workmachine.workflow :as workflow]
            [workmachine.jobs :as jobs]
            [workmachine.work :as work]
            [workmachine.data-input :as input-types]
            [workmachine.data-output :as output-types]
            [workmachine.blueprints.extraction :as extraction-workflow]
            [workmachine.blueprints.content :as content-workflow]))

(defn run-workflow []
  (content-workflow/workflow
   [{:topic_instruction "What topic are you interested in seeing?"
     :research_instrction "Find links to the following topics."
     :write_instruction "Take the links about and come up with a paragraph or two about the topic."}])
  "ok")

(defroutes main-routes
  (GET "/" [] (str (jobs/number-of-available-jobs)))
  (GET "/assignments" [] (str @jobs/assigned-jobs))
  (GET "/available" [] (str @jobs/available-jobs))
  (GET "/finished" [] (str @jobs/finished-jobs))
  (GET "/workflow" [] (run-workflow))
  (GET "/assign/:worker-id" [worker-id] (work/assign worker-id))
  (POST "/submit/:worker-id" {params :params} (work/submit
                                               (params :worker-id)
                                               (dissoc params :worker-id)))
  (GET "/unassign/:worker-id" [worker-id] (work/unassign worker-id))
  (GET "/mturk/serve" (work/assign "1")) ;; Obviously this should not be 1.

  ;; :inputs => [{:type => "string", :name => "name_tag"}]
  ;; :outputs => [{:type => "text", :name => "out_name_tag"}]
  ;; :jobs => [{"name_tag" => "foo"}, {"name_tag" => "bar"}]
  (POST "/workflow/extraction" {params :params} (extraction-workflow/parse-and-run params))
  (route/not-found "<h1>Page not found</h1>"))

(def app (handler/site main-routes))
