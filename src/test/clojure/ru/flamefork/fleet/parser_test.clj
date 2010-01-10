(ns ru.flamefork.fleet.parser-test
  (:use
    [clojure.test]
    [ru.flamefork.fleet.parser]))

(deftest parse-test
  (let [tpl (slurp "src/test/fleet/second/post_tpl.fleet")
        ast (slurp "src/test/fleet/second/post_tpl.ast.clj")]
    (is (= (parse tpl) (read-string ast)))))
