(ns fleet.parser-builder-test
  (:use
    [clojure.test]
    [fleet builder]))

(deftest build-test
  (let [ast (read-string (slurp "src/test/fleet/second/post_tpl.ast.clj"))
        clj (slurp "src/test/fleet/second/post_tpl.clj")]
;    (println (build '(post) ast))
    (is (= (build '(post) ast) clj))))
