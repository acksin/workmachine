(ns workmachine.data-input
  (:use clj-html.core))

;; (defmacro define-field
;;   "Define a field which can be used for input or output."
;;   [name html]
;;   `(defn ~name [name# value#]
;;      {:name ~field-name
;;       :html
      
(defn image [name]
  {:name name
   :html (fn [value]
           [:img {:id name
                  :src value
                  :class "input-field"}])})

(defn audio [name]
  {:name name
   :html (fn [value]
           [:audio {:id name
                    :src value
                    :class "input-field"}])})

(defn text [name]
  {:name name
   :html (fn [value]
           [:span
            {:id name :class "input-field"}
            value])})

(defn instructions [name]
  {:name name
   :html (fn [value]
           [:div {:id name :class "input-field"}
            value])})

(defn parse [field]
  (apply (case (first field)
           :instructions instructions
           :image image
           :text text
           :audio audio)
         (rest field)))
