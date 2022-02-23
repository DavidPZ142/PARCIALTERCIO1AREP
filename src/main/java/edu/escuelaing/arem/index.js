var clima = (function(){

    return {

    conection:function(){
    fetch('http://localhost:4567/consulta?=london')
          .then(response => response.json())
          .then(json => console.log(json))




    }

    }


})();