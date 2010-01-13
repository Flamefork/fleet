(ns ru.flamefork.fleet-test
  (:use
    [clojure.test]
    [ru.flamefork.fleet]))

(defn setup-load-paths
  [f]
  (add-search-path "src/test/fleet/first")
  (add-search-path "src/test/fleet/second")
  (add-search-path "src/test/fleet/third")
  (f)
  (reset! search-paths []))

(use-fixtures :each setup-load-paths)

(def test-posts
  [{:body "First Post"  :tags ["tag1" "tag2" "tag3"]},
   {:body "Second Post" :tags ["tag1" "tag2"]}])

(def test-post
  (first test-posts))

(deftest fleet-test
  (let [tpl  (slurp "src/test/fleet/first/test_tpl.fleet")
        html (slurp "src/test/fleet/first/test_tpl.html")]
    (is (=
      ((fleet [post title] tpl) test-post "Post Template")
      html))))

(deftest deftemplate-test
  (deftemplate single-post
    [post] "<p><(post :body)></p>")
  (is (=
    (single-post test-post)
    "<p>First Post</p>")))

(deftest template-loading-test
  (deftemplate test-tpl [post title])
  (is (=
    (test-tpl test-post "Post Template")
    (slurp "src/test/fleet/first/test_tpl.html"))))

(deftest load-paths-test
  (reset! search-paths [])
  (add-search-path "src/test/fleet/first")
  (add-search-path "src/test/fleet/second")
  (add-search-path "src/test/fleet/third")
  (is (= @search-paths [
    "src/test/fleet/first"
    "src/test/fleet/second"
    "src/test/fleet/third"])))

(deftest sub-template-test
  (deftemplate post-tpl [post])
  (deftemplate posts-tpl [posts])
  (is (=
    (posts-tpl test-posts)
    (slurp "src/test/fleet/second/posts_tpl.html"))))

(def esc-test-posts
  [{:body "Body with \" Quote" :tags ["<evil>tag1</evil>" "tag \"2\"" "tag3"]},
   {:body "qwe<br>asd<br>zxc"  :tags ["tag1" "tag2"]}])

(deftest escaping-test
  (deftemplate post-tpl [post])
  (deftemplate posts-tpl [posts])
  (is (=
    (posts-tpl esc-test-posts)
    (slurp "src/test/fleet/second/esc_posts_tpl.html"))))

(deftest comments-test
  (deftemplate tpl
    [] "<p><(; post :body)><(str \"asd\")></p>")
  (is (=
    (tpl)
    "<p>asd</p>")))
