(ns workmachine.render
  (:require [clj-html.core :as clj-html]))

(defn html [input-fields output-fields job-input]
  (clj-html/html [:div "hi"]))
  ;; (clj-html/html
  ;;  [:div
  ;;   [:h2 "Instructions"]
  ;;   ;; Job input fields
  ;;   (map (fn [input-field]
  ;;          (let [input-field-name (get input-field :name)]
  ;;            ((get input-field :html) (get job-input input-field-name))))
  ;;        input-fields)]
  ;;  [:div
  ;;   [:h2 "Input"]
  ;;   ;; Worker Input fields
  ;;   (map (fn [output-field] (get output-field :html)) output-fields)]))
