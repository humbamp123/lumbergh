(ns lumbergh.events
  (:require
   [re-frame.core :refer [reg-event-db reg-event-fx]]
   [lumbergh.db :as db]
   [day8.re-frame.tracing :refer-macros [fn-traced defn-traced]]))

(reg-event-db
 ::set-active-panel
 (fn-traced [db [_ active-panel]]
            (assoc db :active-panel active-panel)))

;; TODO: Example to look at possibly to expand/goto specified role when it's
;; typed out. The settings for this can be found in the routes
(reg-event-db
 ::set-re-pressed-example
 (fn-traced [db [_ value]]
            (assoc db :re-pressed-example value)))

;;====================lumbergh====================

(reg-event-db
 ::initialize-db
 (fn-traced [_ _]
            (let [db db/default-db]
              (merge db
                     {:owners (into []
                                    (map-indexed
                                     (fn [idx name] {:id idx :label name})
                                     (:owners db)))}))))

(defn remove-owner
  [current-role-owners owner-id]
  (flatten
   (mapcat
    #(vector (first %) {:owner nil})
    (filter
     (fn [[_ {role-owner :owner}]]
       (= (:id role-owner) owner-id))
     current-role-owners))))

(defn index-of-owner
  [owners owner]
  (first
   (keep-indexed
    (fn [idx x]
      (when (= owner x)
        idx))
    owners)))

(reg-event-db
 ::update-owner
 (fn [{:keys [current-role-owners owners] :as db} [_ current-role owner-id]]
   (let [remove-owner  (remove-owner current-role-owners owner-id)
         previous-role (first remove-owner)
         owner         (nth owners owner-id)
         updated-owner (assoc owner
                              :previous-role previous-role
                              :current-role  current-role)]
     (-> (update-in db [:current-role-owners]
                    #(apply assoc % remove-owner))
         (assoc-in [:owners owner-id] updated-owner)
         (assoc-in [:current-role-owners current-role :owner] updated-owner)))))
