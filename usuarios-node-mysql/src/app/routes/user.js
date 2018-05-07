const dbConnection = require('../../config/dbConnection');

module.exports = app => {

  const connection = dbConnection();

  app.get("/",(rep,res)=>{
    res.send("hola mundo")
  });
  app.get('/user', (req, res) => {
    connection.query('SELECT * FROM user', (err, result) => {
      res.render('user/user', {
        user: result
      });
    });
  });

  app.post('/user', (req, res) => {
    const { title, news } = req.body;
    connection.query('INSERT INTO user SET ? ',
      {
        title,
        user
      }
    , (err, result) => {
      res.redirect('/user');
    });
  });
};
