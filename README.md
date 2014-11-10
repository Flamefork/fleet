### Fleet

[![Build Status](https://travis-ci.org/Flamefork/fleet.png?branch=master)](https://travis-ci.org/Flamefork/fleet)

[![Clojars Project](http://clojars.org/fleet/latest-version.svg)](http://clojars.org/fleet)

Templating System for Clojure

## Gist

0. Template is function of its arguments.
0. HTML is better for HTML than some host language DSL (just cause HTML *is* DSL).
0. DOM manipulation tools and XSLT are good for transforming, not for templating (yes, opinionated).
0. Clojure is good :)
0. HTML isn't the only language that needs templating.

## Brief

Write
```clojure
<p><(post :body)></p>
```
instead of
```clojure
<p><%= (escape-html (post :body)) %></p>
```
Read on for more goodness.

## Template Language

### Main Fleet construction is Spaceship `<()>`.

...just because (star)fleet consists of many spaceships.

`<()>` is almost equivalent to Clojure's `()`, so
`<h1><(body)></h1>` in Fleet is nearly the same as `(str "<h1>" (body) "</h1>")` in Clojure.

The only difference is that `(body)` output gets escaped (e.g. html-encoded to prevent XSS).  
Use `raw` function to prevent escaping: `<(raw "<br/>")>`.  
Use `str` function to place value `<(str posts-count)>` instead of calling a function.

This is almost all we need, with one issue: writing something like
```clojure
<(raw (for [p posts]
  (str "<li class=\"post\">" (p :title) "</li>")))>
```
is too ugly, and defining `<li class="post"><(p :title)></li>` as separate template
can be overkill in many cases. So there should be the good way of embedding strings and anonymous templates.

### Slipway construction `"><"` is for embedding strings.

The previous example could be rewritten using Slipway as
```clojure
    <(for [p posts] ">
      <li class="post"><(p :title)></li>
    <")>
```

This example has two points worth mentioning.
Result of `"><"` construction processing is an expression of String type.
Strings in Slipway considered `raw` by default.

Next case is something like this:
```clojure
    <(raw (map (fn [post]
      (str "<li class=\"post\">" (post :title) "</li>")) posts))>
```

With Slipway it can be replaced with
```clojure
    <(map (fn [post] ">
      <li class="post"><(post :title)></li>
    <") posts)>
```

Need to mention that all this supports lexical scoping and other Clojure features just like reference (previous) expression.

## Functions

### Single anonymous template: `fleet`

```clojure
(fleet [& args] template-str options)
```

Creates anonymous function from `template-str` using provided `options` map. Intended to use just like `(fn` construct.

Example:

```clojure
(def footer (fleet "<p>&copy; <(year (now))> Your Company</p>"))
(println (footer))

(def header (fleet [title] "<head><title><(str title)></title></head>"))
(println (header "Main Page"))
```

Main option is `:escaping`. It can be function of one String argument or keyword specifying one of predefined functions:  
`:bypass` — default, no escaping;  
`:xml` — XML (or HTML) rules;  
`:str` — Java-compatible string escaping;  
`:clj-str` — Clojure string escaping (`\n` is allowed);  
`:regex` — Escaping of Regex special symbols.

Options `:file-name` and `:file-path` (both String) are in place for better stack traces.

### Template namespace: `fleet-ns`

```clojure
(fleet-ns root-ns root-path filters)
```

Treats `root-path` as root of template directory tree, maps it to namespace with prefix `root-ns.`, creates template functions
for each file in it with name and samespace according to relative path.

Example:

```clojure
(fleet-ns view "path/to/view_dir" [:fleet :xml])
```

Template functions are created by the following rules:

— Several equal functions will be created for each file. E.g. file `posts.html.fleet` will produce 3 functions: `posts`, `posts-html` and `posts-html-fleet`.

This is useful for cases where you have `posts.html.fleet` and `posts.json.fleet`, so you may access distinct templates as `posts-html` and `posts-json`,
while and if you have only one `posts.html.fleet` you could call it `posts` conviniently.

— Template function will take one or two arguments: first named same as shortest function name for file (`posts` in previous example) and second named `data`.

When it's called with one arguments both symbols (fn-name and data) are bound to same value of this argument.  
When it's called with no arguments both symbols (fn-name and data) are bound to nil.  
This is also for convinience: you could use name appropriate to usage: e.g. if your template renders post, you could use `post` param name,
and if template renders some complex data you could use `data`.
Also you can mix&match, for example `post` as main rendered entity and `data` as some render options.

Filters argument is vector of `file-filter escaping-fn` pairs used to filter which files to process and with which escaping function.
File filters could be defined as function, string, regex, `:fleet` or `:all`.  
— Function should have Boolean type and one File argument.  
— String filter definition treated as `*.string.fleet` mask, e.g. `"js"` mask will match `update.js.fleet`.  
— Regex filter matches whole filename, e.g. `#".*.html"` will match `posts.html`.  
— `:fleet` filter is treated as "others". If it is set all `*.fleet` files will be processed.  
— `:all` means, literally, all.

### More on escaping

If you need to insert Fleet constructions into text you can escape them using backslash.  
You only need escaping to remove ambiguity,
so use `\<(` and `\<"` only outside embedded clojure code,  `\">` and `\)>` only inside embedded clojure code.

## Examples

This is not intended to work out-of-box, only to show some bits of a language / system.

### Language

Template file (`post_dedicated.fleet`):

```clojure
<head>
  <title><(post :title)></title>

  <(stylesheet :main)>
  <(raw "<script>alert('Hello!')</script>")>
</head>
<body>

<p><(str notice)></p>

<p>Spaceship \<()> is landing.</p>

<(
; Begin of post
)>
<(inside-frame (let [p post] ">
  Author: <(p :author)><br/>
  Date: <(p :date)><br/>
<"))>

<p><(post :body)></p>
<ul>
  <(for [tag (post :tags] ">
    <li><(str tag)></li>
  <")>
</ul>
<(
; End of post
)>

<(footer)>
</body>
</html>
```

Clojure:

```clojure
(def post-page (fleet [post] (slurp "post_dedicated.fleet")))

(post-page p)

(footer)
```

### API

Low-level:

```clojure
(def footer (fleet "<p>&copy; <(year (now))> Flamefork</p>"))
```

High-level:

Directory tree

```
root_dir/
  first_subdir/
    file_a.html.fleet
    file_b.html.fleet
  second_subdir/
    file_c.html.fleet
```
        
will be treated and processed by `(fleet-ns templates "path/to/root_dir" [:fleet :xml])` as functions

```
templates.first-subdir/file-a
templates.first-subdir/file-b
templates.second-subdir/file-c
```
    
and (for example) first function will be like

```clojure
(defn file-a
  ([file-a data] ...)
  ([file-a] (recur file-a file-a)))
  ([] (recur nil nil)))
```

## Compatibility

Use 0.9.x for Clojure 1.2, 1.3  
Use 0.10.x for Clojure 1.4+

## Roadmap

- update Fleet with latest Clojure goodness [in progress]
- support ClojureScript

## License

Copyright (c) 2010 Ilia Ablamonov, released under the [MIT license](http://www.opensource.org/licenses/mit-license.php).
