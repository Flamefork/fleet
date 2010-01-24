(ns ru.flamefork.fleet-test
  (:use
    [clojure.test]
    [ru.flamefork.fleet]))

(defmacro fileet
  "Convinient way to define test templates"
  [args filename]
  `(fleet ~args (slurp (str "src/test/fleet/" ~filename ".fleet")) clojure.contrib.lazy-xml/escape-xml))

(defmacro readhtml
  "Convinient way to read test data"
  [filename]
  `(slurp (str "src/test/fleet/" ~filename ".html")))

(def test-posts
  [{:body "First Post"  :tags ["tag1" "tag2" "tag3"]},
   {:body "Second Post" :tags ["tag1" "tag2"]}])

(def test-post
  (first test-posts))

(deftest fleet-test
  (is (=
    ((fileet [post title] "first/test_tpl") test-post "Post Template")
    (readhtml "first/test_tpl"))))

(deftest deftemplate-test
  (def single-post (fleet [post] "<p><(post :body)></p>" clojure.contrib.lazy-xml/escape-xml))
  (is (=
    (single-post test-post)
    "<p>First Post</p>")))

(deftest sub-template-test
  (def post-tpl (fileet [post] "second/post_tpl"))
  (def posts-tpl (fileet [posts] "second/posts_tpl"))
  (is (=
    (posts-tpl test-posts)
    (readhtml "second/posts_tpl"))))

(def esc-test-posts
  [{:body "Body with \" Quote" :tags ["<evil>tag1</evil>" "tag \"2\"" "tag3"]},
   {:body "qwe<br>asd<br>zxc"  :tags ["tag1" "tag2"]}])

(deftest escaping-test
  (def post-tpl (fileet [post] "second/post_tpl"))
  (def posts-tpl (fileet [posts] "second/posts_tpl"))
  (is (=
    (posts-tpl esc-test-posts)
    (readhtml "second/esc_posts_tpl"))))

(deftest comments-test
  (def tpl (fleet [] "<p><(; post :body)><(str \"asd\")></p>" clojure.contrib.lazy-xml/escape-xml))
  (is (= (tpl) "<p>asd</p>")))

(deftest anonymous-tpls-test
  (def anon-posts-tpl (fileet [posts] "third/anon_posts_tpl"))
  (is (=
    (anon-posts-tpl test-posts)
    (readhtml "third/anon_posts_tpl"))))

