Comentarios:
- En principio no se van a validar las entradas (por ejemplo con @Valid). Los requisitos no lo piden y voy justo de tiempo. Sería interesante añadirlo en algún momento.
- En relación con las cuentas se han implementado 4 funcionalidades:
  - ver todas las cuentas en get(/accounts)
  - ver una cuenta en get(/accounts/{accountId})
  - crear una cuenta en post(/accounts)
  - editar una cuenta en put(/accounts/{accountId})
- Se ha decidido de que put tambien devuelva status 201(created)
- Las transferencias se van a modelar mediante la entidad 'Transfer'
- las transferencia son realizadas en post(/transfers), pasando como argumento un 'Transfer' en json
