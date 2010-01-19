(ns ru.flamefork.fleet.parser-builder-test
  (:use
    [clojure.test]
    [ru.flamefork.fleet parser builder]))

(deftest parse-test
  (let [tpl (slurp "src/test/fleet/second/post_tpl.fleet")
        ast (slurp "src/test/fleet/second/post_tpl.ast.clj")]
    (is (= (parse tpl) (read-string ast)))))

(deftest build-test
  (let [ast (read-string (slurp "src/test/fleet/second/post_tpl.ast.clj"))
        clj (slurp "src/test/fleet/second/post_tpl.clj")
        src (build '(post) ast str)
        replaced-src (clojure.walk/prewalk-replace {str 'str} src)]
    (is (=
      replaced-src
      (read-string clj)))))
