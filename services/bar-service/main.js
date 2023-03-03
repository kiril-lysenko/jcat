console.log('Hello New World')
    const checkAge = function (age) {
        if (age < age_2) {
           console.log ( "You don't have access cause your age is "  + (age) + " It's less then ")
           }
           else if   (age >= age_2 && age < age_3) {
           console.log ("Welcome")}
       
           else if  (age > age_3) { 
           console.log ("Keep calm and look Culture channel"); }
           
           else  {
           console.log ("Technical work");}
   }
   checkAge(17)
   checkAge(18)
   checkAge(61)

    // 2* Преобразовать задание 1* таким образом, чтобы первым делом в функции проверялся тип данных. И если он не Number - кидалась ошибка.

    const checkAge2 = function (age) {
    if (typeof age == "number") {
    
        if (age < age_2) {
           console.log ( "You don't have access cause your age is "  + (age) + " It's less then ")}

           else if   (age >= age_2 && age < age_3) {
           console.log ("Welcome")}
       
           else if  (age > age_3) { 
           console.log ("Keep calm and look Culture channel"); }
           
           else  {
           console.log ("Technical work"); }
    }else { console.log ("Not integer value") }
    }
    
    checkAge2(17)
    checkAge2(18)
    checkAge2(61)
    checkAge2("xccv")
