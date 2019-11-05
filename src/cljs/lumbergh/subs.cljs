(ns lumbergh.subs
  (:require
   [re-frame.core :refer [reg-sub]]))

(reg-sub
 ::name
 (fn [db]
   (:name db)))

(reg-sub
 ::active-panel
 (fn [db _]
   (:active-panel db)))

(reg-sub
 ::re-pressed-example
 (fn [db _]
   (:re-pressed-example db)))

;;====================lumbergh====================

(reg-sub
 ::owners
 (fn [db]
   (:owners db)))

(reg-sub
 ::roles
 (fn [db]
   (:roles db)))

(reg-sub
 ::current-role-owners
 (fn [db _]
   (:current-role-owners db)))

(reg-sub
 ::role-owner
 :<- [::current-role-owners]
 (fn [current-role-owners [_ role]]
   (get-in current-role-owners [role :owner])))
