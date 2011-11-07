### Fleet

Templating System for Clojure

## Gist

0. Template is function of its arguments.
0. HTML is better for HTML than some host language DSL (just cause HTML *is* DSL).
0. DOM manipulation tools and XSLT are good for transforming, not for templating (yes, opinionated).
0. Clojure is good :)
0. HTML isn't the only language that needs templating.

## Why Fleet?

Because  
— I wanted template engine for Clojure  
— close to Clojure  
— and I hate writing HTML not in HTML (clj-html, haml, etc).

So I've started by reviewing few Clojure and CL implementations of ERB/JSP-like templates. Their syntax looks like  
`<p><%= (post :body) %></p>`  
or  
`<p><?clj (post :body) ?></p>`  
Also, in order to prevent XSS attacks, one needs to append some `escape-xml` function call to all this code:  
`<p><%= (escape-html (post :body)) %></p>` or (Rails-like) `<p><%=(h(post :body))%></p>`  
10 chars `<%=(h())%>` just to safely insert expression value seems to be a little too much...

Then I realized that the following syntax is usable:  
`<p><(post :body)></p>`  
Not a big deal, but... that's all. Really, `<%= (escape-html ...` is here, and all other constructions
are here too.

Rails-like partials like  
`<%= render :partial => 'post', :collection => posts %>`  
are here too:  
`<(map post-tpl posts)>`  
...just plain Clojure code.

Need to bypass escaping?  
`<(raw "<script>alert('Hello!')</script>")>`

Not writing HTML at all? Use fleet with default (bypassing) escape function.  
Use some `escape-mylang` to work with other languages.

## API

### Single anonymous template: `fleet`

`(fleet [& args] template-str options)`

Creates anonymous function from `template-str` using provided `options` map.

Main option is `:escaping`. It can be function of one String argument or keyword specifying one of predefined functions:  
`:bypass` — default, no escaping;  
`:xml` — XML (or HTML) rules;  
`:str` — Java-compatible string escaping;  
`:clj-str` — Clojure string escaping (`\n` is allowed);  
`:regex` — Escaping of Regex special symbols.

Options `:file-name` and `:file-path` of type String are used for error reporting (e.g. file-name will be in stacktrace along with line number).

### Template namespace: `fleet-ns`

`(fleet-ns root-ns root-path filters)`

Treats `root-path` as root of template namespace with prefix `root-ns.` and creates template functions
for each file in it with name and samespace according to relative path.

Template function creation conventions:   
— Several functions will be created for each file. E.g. file `posts.html.fleet` will produce 3 functions: `posts`, `posts-html` and `posts-html-fleet`.  
— Template function will take one or two arguments: first named same as shortest function name for file (`posts` in previous example) and second named `data`.  
— When it's called with one arguments both symbols (fn-name and data) are bound to same value of this argument.  
— When it's called with no arguments both symbols (fn-name and data) are bound to nil.

Filters argument is vector of `file-filter escaping-fn` pairs used to filter which files to process and with which escaping function.
File filters could be defined as function, string, regex, :fleet or :all.  
— Function should have Boolean type and one File argument.  
— String filter definition treated as `*.string.fleet` mask, e.g. `"js"` mask will match `update.js.fleet`.  
— Regex filter matches whole filename, e.g. `#".*.html"` will match `posts.html`.  
— `:fleet` filter is treated as "others". If it is set all `*.fleet` files will be processed.  
— `:all` means, literally, all.

## Template Language

### Main Fleet construction is Spaceship `<()>`.

...just cause (star)fleet consists of many spaceships.

`<()>` is almost equivalent to `()`, so
`<h1><(body)></h1>` in Fleet is nearly the same as `(str "<h1>" (body) "</h1>")` in Clojure.

The only difference is that `(body)` output gets escaped (e.g. html-encoded to prevent XSS).  
Use `raw` function to prevent encoding: `<(raw "<br/>")>`.  
Use `str` function to place value `<(str posts-count)>`.

This seems to be complete system, but writing something like

    <(raw (for [p posts]
      (str "<li class=\"post\">" (p :title) "</li>")))>
      
is too ugly..  
And defining `<li class="post"><(p :title)></li>` as separate template
can be overkill in many cases. So there should be the way of embedding strings and anonymous templates.

### Slipway construction `"><"` intended for embedding strings.

The previous example could be rewritten using Slipway as

    <(for [p posts] ">
      <li class="post"><(p :title)></li>
    <")>

This example has two points worth mentioning.
Result of `"><"` construction processing is an expression of String type.
Strings in Slipway concidered `raw` by default.

Next case is something like this:

    <(raw (map (fn [post]
      (str "<li class=\"post\">" (post :title) "</li>")) posts))>

With Slipway it can be replaced with

    <(map (fn [post] ">
      <li class="post"><(post :title)></li>
    <") posts)>

Need to mention that all this supports lexical scoping and other Clojure fectures just like reference (previous) expression.

### More on escaping

If you need to insert Fleet constructions into text you can escape them using backslash.  
You only need escaping to remove ambiguity,
so use `\<(` and `\<"` only outside embedded clojure code,  `\">` and `\)>` only inside embedded clojure code.

## Examples

This is not intended to work out-of-box, only to show some bits of a language / system.

### Language

Template file (`post_dedicated.fleet`):

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

Clojure:

    (deftemplate post-page [post] "post_dedicated")
    
    (footer)

    (post-page p)

### API

Low-level:

    (def footer (fleet "<p>&copy; <(.get (Calendar/getInstance) Calendar/YEAR)> Flamefork</p>"))

High-level:

Directory tree

    root_dir/
      first_subdir/
        file_a.html.fleet
        file_b.html.fleet
      second_subdir/
        file_c.html.fleet
        
will be treated and processed by `(fleet-ns templates "path/to/root_dir" [:fleet :xml])` as functions

    templates.first-subdir/file-a
    templates.first-subdir/file-b
    templates.second-subdir/file-c
    
and (for example) first function will be like

    (defn file-a
      ([file-a data] ...)
      ([file-a] (recur file-a file-a)))
      ([] (recur nil nil)))

## License

Copyright (c) 2010 Ilia Ablamonov, released under the MIT license.