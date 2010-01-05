(ns ru.flamefork.fleet-test
  (:use
    [clojure.test]
    [ru.flamefork.fleet]))

(def test-post
  {:body "Body" :tags ["tag1" "tag2" "tag3"]})

(deftest fleet-test
  (let [tpl  (slurp "src/test/fleet/first/test_tpl.fleet")
        html (slurp "src/test/fleet/first/test_tpl.html")]
    (is (=
      ((fleet '(post title) tpl) test-post "Post Template")
      html))))

(deftest deftemplate-test
  (deftemplate single-post
    [post] "<p><(post :body)></p>")
  (is (=
    (single-post test-post)
    "<p>Body</p>")))

(deftest template-loading-test
  (deftemplate test-tpl [post title])
  (is (=
    (test-tpl test-post "Post Template")
    (slurp "src/test/fleet/first/test_tpl.html"))))
