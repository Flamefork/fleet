(ns fleet.parser2-test
  (:use
    [clojure.test]
    [fleet parser2]))

(deftest parse-test
  (let [tpl (slurp "src/test/fleet/second/post_tpl.fleet")
        ast (slurp "src/test/fleet/second/post_tpl.ast.clj")]
    (is (= (parse tpl) (read-string ast)))))
