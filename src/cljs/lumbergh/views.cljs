(ns lumbergh.views
  (:require
   [re-frame.core :refer [subscribe dispatch]]
   [re-com.core :as re-com]
   [breaking-point.core :as bp]
   [re-pressed.core :as rp]
   [lumbergh.events :as events]
   [lumbergh.subs :as subs]))


;; home

(defn display-re-pressed-example
  []
  (let [re-pressed-example (subscribe [::subs/re-pressed-example])]
    [:div

     [:p
      [:span "Re-pressed is listening for keydown events. A message will be displayed when you type "]
      [:strong [:code "hello"]]
      [:span ". So go ahead, try it out!"]]

     (when-let [rpe @re-pressed-example]
       [re-com/alert-box
        :alert-type :info
        :body rpe])]))

(defn home-title
  []
  [:h1 "Lumbergh"])

(defn link-to-about-page []
  [re-com/hyperlink-href
   :label "go to About Page"
   :href "#/about"])


;;====================lumbergh====================

(defn assigned-role
  [role idx]
  (let [owners     (subscribe [::subs/owners])
        {:keys [id previous-role]} @(subscribe [::subs/role-owner role])]
    [:div.flex.justify-between.center
     [re-com/single-dropdown
      :width "150px"
      :choices owners
      :on-change #(dispatch [::events/update-owner role %])
      :model id
      :placeholder "Unassigned"
      :tab-index idx]
     [:span.col-3 previous-role]
     [:span.col-3 role]]))

;; TODO: Separate into `roler` into its own namespace
(defn roler-section
  ;; TODO: Refactor to use airtable/sheets data
  []
  (fn []
    (let [roles @(subscribe [::subs/roles])]
      [:div.m3.p3.border.border-purple.rounded.max-580
       [:h2 "Roler"]
       [:div.flex.justify-between.center
        [:h3.col-3 "Team Member"]
        [:h3.col-3 "Previous Role"]
        [:h3.col-3 "Current Role"]]
       (map-indexed
        (fn [idx role]
          (with-meta [assigned-role role idx] {:key idx}))
        roles)])))

(defn story-groomer
  []
  (fn []
    [:div.m3.p3.border.border-purple.rounded.max-580
     [:h2 "Story Groomer"]]))

(defn deployer
  []
  (fn []
    [:div.m3.p3.border.border-purple.rounded.max-580
     [:h2 "Deployer"]]))

(defn summarizer
  []
  (fn []
    [:div.m3.p3.border.border-purple.rounded.max-580
     [:h2 "Summarizer"]]))

(defn system-wellness
  []
  (fn []
    [:div.m3.p3.border.border-purple.rounded.max-580
     [:h2 "System Wellness"]]))

(defn home-panel
  []
  [:div
   [home-title]
   ;; TODO: Add routes and tabs for each role
   #_[link-to-about-page]
   #_[display-re-pressed-example]
   #_[:div
      [:h3 (str "screen-width: " @(subscribe [::bp/screen-width]))]
      [:h3 (str "screen: " @(subscribe [::bp/screen]))]]
   [roler-section]
   [story-groomer]
   [deployer]
   [summarizer]
   [system-wellness]])


;; about

(defn about-title
  []
  [re-com/title
   :label "This is the About Page."
   :level :level1])

(defn link-to-home-page
  []
  [re-com/hyperlink-href
   :label "go to Home Page"
   :href "#/"])

(defn about-panel
  []
  [re-com/v-box
   :gap "1em"
   :children [[about-title]
              [link-to-home-page]]])


;; main

(defn- panels
  [panel-name]
  (case panel-name
    :home-panel [home-panel]
    :about-panel [about-panel]
    [:div]))

(defn show-panel
  [panel-name]
  [panels panel-name])

(defn main-panel
  []
  (fn []
    (let [active-panel (subscribe [::subs/active-panel])]
     [panels @active-panel])))
