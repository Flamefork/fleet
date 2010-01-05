# Fleet

Fleet is a FLExiblE Templates for Clojure.

## Gist

Template is function of its arguments that returns string. Nothing more.

## Why Fleet?

Because  
— I wanted template engine for Clojure  
— close to Clojure in syntax and style  
— and I hate writing HTML not in HTML (clj-html, haml, etc).

With pragmatism and aesthetics at the first place
I reviewed few Clojure and CL implementations of ERB/JSP-like templates. Their syntax looks like  
`<p><%= (post :body) %></p>`  
or  
`<p><?clj (post :body) ?></p>`  
Also, in order to prevent XSS attacks, one needs to append some `escape-xml` function call to all this code:  
`<p><%= (escape-html (post :body)) %></p>`  
A little too much...

Then I realized that the following syntax is usable:  
`<p><(post :body)></p>`  
Not a big deal, but... that's all. Really, `<%= (escape-html ...` is here, and all other constructions
are here too.

Rails-like things like  
`<%= render :partial => 'post', :collection => posts %>`  
are here too:  
`<(map post-tpl posts)>`  
...just plain Clojure code.

Need to bypass escaping?  
`<(raw "<script>alert('Hello!')</script>")>`  
Not writing HTML at all? Changing `escape-fn` to e.g. `str` will disable escaping.

## Roadmap

0. `DONE` Language design
0. `DONE` API design
0. `DONE` Parser
0. `DONE` Compiler
0. `DONE` Infrastructure
0. Auto HTML-escaping
0. Cleanup
0. Reimplement from scratch ;)

`DONE` = First draft / first working version :)

## API

`(fleet '(list of args) template-str)`  
Creates anonymous function from template-str.

`(deftemplate fn-name [& args] source?)`  
Creates function with name fn-name and defined args.  
If source is defined, it's parsed as String containing template.  
If source is not defined, file `fn_name.fleet` found in one of `@search-paths` is loaded and parsed.

`search-paths`  
List of paths used for template search (Atom).

`escape-fn`  
Function for escaping XML tags & entities (Atom).

## Template Language

Main Fleet construction is Spaceship `<()>`, just cause (star)fleet consists of many spaceships.

`<()>` is almost equivalent to `()`, so
`<h1><(body)></h1>` in Fleet is nearly the same as `(str "<h1>" (body) "</h1>")` in Clojure.

The only difference is that `(body)` output gets html-encoded to prevent XSS.
Use `raw` function to prevent encoding: `<(raw "<br/>")>`.
Use `str` function to place value `<(str posts-count)>`.

This seems to be complete system, but writing something like
    <(raw (for [p posts]
      (fleet "<li class=\"post\"><(p :title)></li>")))>
is too ugly.. And defining `<li class="post"><(p :title)></li>` as separate template
can be overkill in many cases. So there should be the way of defining anonymous templates and applying them inplace.

Slipway construction `"><"` intended for defining anonymous template and applying it.
The previous example could be rewritten using Slipway as
    <(for [p posts] ">
      <li class="post"><(p :title)></li>
    <")>

This example has two points worth mentioning.
Result of `"><"` is String, not anonymous template.
Also Spaceship with Slipway attached concidered `raw` by default.

## Examples

Template file (`post_dedicated.fleet`):
    <head>
      <title><(post :title)></title>

      <(stylesheet :main)>
      <(raw "<script>alert('Hello!')</script>")>
    </head>
    <body>
    
    <p><(str notice)></p>
    
    <(comment Begin of post)>
    <(inside-frame (let [p post] ">
      Author: <(p :author)><br/>
      Date: <(p :date)><br/>
    <"))>

    <p><(post :body)></p>
    <ul>
      <(for [tag (post :tags] ">
        <li><(tag)></li>
      <")>
    </ul>
    <(comment End of post)>

    <(footer)>
    </body>
    </html>
Clojure:
    (def footer (fleet "<p>&copy; <(.get (Calendar/getInstance) Calendar/YEAR)> Flamefork</p>"))

    (deftemplate post-page [post] "post_dedicated")
    
    (footer)

    (post-page p)
