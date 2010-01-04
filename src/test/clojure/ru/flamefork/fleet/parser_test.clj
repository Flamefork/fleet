(ns ru.flamefork.fleet.parser-test
  (:use
    [clojure.test]
    [ru.flamefork.fleet.parser]))

(deftest parse-test
  (let [tpl (slurp "src/test/fleet/second/post.fleet")
        ast (read-string (slurp "src/test/fleet/second/post.ast.clj"))]
    (is (= (parse tpl) ast))))
