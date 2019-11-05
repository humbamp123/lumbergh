(ns lumbergh.db)

(def default-db
  {:name                "re-frame"
   :owners              ["example-coworker name"]
   :roles               [:story-groomer
                         :deployer
                         :summarizer
                         :system-wellness
                         :roler]
   :current-role-owners {:story-groomer   {:owner nil}
                         :deployer        {:owner nil}
                         :summarizer      {:owner nil}
                         :system-wellness {:owner nil}
                         :roler           {:owner nil}}})
