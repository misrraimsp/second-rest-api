Comentarios:
- En principio no se van a validar las entradas (por ejemplo con @Valid). Los requisitos no lo piden y voy justo de tiempo. Sería interesante añadirlo en algún momento.
- En relación con las cuentas se han implementado 4 funcionalidades:
  - ver todas las cuentas en *get(/accounts)*
  - ver una cuenta en *get(/accounts/{accountId})*
  - crear una cuenta en *post(/accounts)*
  - editar una cuenta en *put(/accounts/{accountId})*
- Se ha decidido de que *put* tambien devuelva status 201(created)
- Las transferencias se van a modelar mediante la entidad 'Transfer'
- las transferencia son realizadas en *post(/transfers)*, pasando como argumento un 'Transfer' en json
- Se ha usado la anotación @Transactional para garantizar que las transferencias se realizan de forma transaccional
- La propagación de la transacción se ha fijado a 'nested', por considerarla la más apropiada ([fuente](https://thorben-janssen.com/transactions-spring-data-jpa/))
- cuando se intenta una transacción no permitida o cuando se pretende utilizar una cuenta que no existe, el sistema devuelve un mensaje de error en formato json
- en ppio no se ha visto necesidad de testar la capa de persistencia ya que no se han añadido nuevas queries a las heredadas desde JpaRepository
- Sería interesante tener en cuenta la moneda a la hora de hacer las transacciones. Por ejemplo se podría crear un servicio que proporcionara la tasa de cambio. En el presente ejercicio, por simplicidad y al no estar explícitamente indicado en los requisitos se ha obviado esta cuestión.


Comments:
- Inputs will not be validated (for example with @Valid). The requirements do not ask for it, and I am short on time. It would be interesting to add it at some future point.
- 4 accounts functionalities have been implemented:
  - see all accounts at *get(/accounts)*
  - see an account at *get(/accounts/{accountId})*
  - create an account at *post(/accounts)*
  - edit an account at *put(/accounts/{accountId})*
- It has been decided that *put* also returns status 201
- The transfers will be modeled by the entity 'Transfer'
- transfers are perform at *post(/transfers)*, passing as argument a 'Transfer' in json
- The @Transactional annotation has been used to ensure that transfers are perform transactionally
- The transaction propagation has been set to 'nested', considering it the most appropriate ([source] (https://thorben-janssen.com/transactions-spring-data-jpa/))
- when trying an illegal transaction or when trying to use an account that doesn't exist, the system returns an error message in json format
- there has been no need to unit test the persistence layer since no new queries have been added to the ones inherited from JpaRepository
- It would be interesting to take the currency into account when making transactions. For example, a service could be created to provide the exchange rate. In this exercise, for simplicity and due to not being explicitly indicated in the requirements, this issue has been ignored.