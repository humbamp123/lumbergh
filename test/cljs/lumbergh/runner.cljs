(ns lumbergh.runner
    (:require [doo.runner :refer-macros [doo-tests]]
              [lumbergh.core-test]))

(doo-tests 'lumbergh.core-test)
