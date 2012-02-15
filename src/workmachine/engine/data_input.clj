(ns workmachine.engine.data-input)

(defmacro define-input
  [input-name html]
  `(defn ~input-name [fname#]
     (let [~'name fname#]
       {:name fname#
        :html ~html})))
      

(define-input image
  (fn [value]
    [:img {:id name
           :src value
           :class "input-field"}]))

(define-input audio
  (fn [value]
    [:audio {:id name
             :src value
             :class "input-field"}]))

(define-input text
  (fn [value]
    [:span
     {:id name :class "input-field"}
     value]))

(define-input instructions
  (fn [value]
    [:div {:id name :class "input-field"}
     value]))

(define-input identifier
  (fn [value]
    ;; No-op
    ))

(defn parse [field]
  (apply (case (first field)
           :instructions instructions
           :image image
           :text text
           :audio audio)
         (rest field)))
