<#-- @ftlvariable title="" type="org.neo4j.hackathons.MovieView" -->
  <!doctype html>
  <html>

  <head>
    <title>${title} [${released}] - Hackathon Movie Guide</title>
    <link rel="stylesheet" href="/assets/css/main.css">
  </head>

  <body>

  <div class="header">
    <nav><a href="/">Hackathon Movie Guide</a> / <a href="/movie/">Movies</a> / <strong>${title}</strong></nav>
  </div>

  <h1>${title}</h1>

  <h2>Movie Details</h2>
  <dl>
    <dt>Title:</dt>
    <dd>${title}</dd>
    <dt>Released:</dt>
    <dd>${released}</dd>
    <dt>Director:</dt>
    <dd><a href="/person/${director}">${director}</a></dd>
  </dl>

  <h2>Cast</h2>
  <ul>
    <#list actors as actor>
      <li><a href="/person/${actor.name}">${actor.name}</a></li>
    </#list>
  </ul>

  <h2>Comments</h2>

    <#list comments as comment>
      <p>On ${comment.date}, ${comment.name} said...
      <blockquote>${comment.text}</blockquote>
      </p>
    </#list>



  <form method="POST" action="comment">
    <h3>Submit a new comment</h3>

    <input type="hidden" name="title" value="${title}">

    <label>Name:<br>
      <input type="text" name="name" value="">
    </label><br>

    <label>Comments:<br>
      <textarea name="text" cols="80" rows="6"></textarea>
    </label><br>

    <input type="submit" value="Submit">

  </form>

  <div class="footer">
    <code>(graphs)-[:ARE]->(everywhere)</code>
    <p>With &hearts; from Sweden &amp; the <a href="http://neo4j.com/community/">Neo4j Community</a></p>
  </div>

  </body>

  </html>
