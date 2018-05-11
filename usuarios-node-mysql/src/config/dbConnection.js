const mysql = require('mysql');

module.exports = () => {
  return mysql.createConnection({
    host: 'ocvwlym0zv3tcn68.cbetxkdyhwsb.us-east-1.rds.amazonaws.com',
    user: 'mfh5oxm25hprrpon',
    password: 'etk70aoilrc5y8nz',
    database: 'g3gcrh40646vhxdn'
  });
}
