<#-- @ftlvariable title="" type="org.neo4j.hackathons.MovieView" -->
  <!doctype html>
  <html>

  <head>
    <title>${title} [${released}] - Hackathon Movie Guide</title>
    <link rel="stylesheet" href="/assets/css/main.css">
  </head>

  <body>

  <div class="header">
    <nav><a href="/">Hackathon Movie Guide</a> / <a href="/movies/">Movies</a> / <strong>${title}</strong></nav>
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

  <div class="footer">
    <code>(graphs)-[:ARE]->(everywhere)</code>
    <p>With &hearts; from Sweden &amp; the <a href="http://neo4j.com/community/">Neo4j Community</a></p>
  </div>

  </body>

  </html>
