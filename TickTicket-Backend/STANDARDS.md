# Standards

### General

Repositories
- Extend ```JpaRepository``` instead of ```CrudRepository```
- Prefer ```Optional``` for return types instead of ```null``` checking. Example [here](src/main/java/tickticket/dao/TicketRepository.java) (L19) and benefit [here](src/main/java/tickticket/service/TicketService.java) (L40)

Services
- Use ```@AllArgsConstructor``` on the class and remove all ```@Autowired``` annotations
- Always have a function to **create**, **find by ID**, **find all** and **delete by ID**

DTOs
- Use ```@Data``` on the class. This will generate getters, setters, equals, hashcode and toString automatically
- Put the conversion code to and from DTOs inside the DTO class as static functions. Example [here](src/main/java/tickticket/dto/TicketDTO.java)

Controllers
- Always have a function to **create**, **find by ID**, **find all** and **delete by ID**
- Don't use ```@RequestParam``` to pass in all the fields of an object. In fact, ```@RequestParam``` shouldn't be used at all for now. Instead, use ```@RequestBody``` and pass in the DTO. Example [here](src/main/java/tickticket/controller/TicketController.java) (L27)
- Please follow the [RESTful API naming guidelines](https://restfulapi.net/resource-naming/), don't free for all. If you don't want to read, look at the [TicketController](src/main/java/tickticket/controller/TicketController.java) for an example

### Testing

Don't wing it. Make tests small and concise and test a single thing at a time.

### Recommendations

- Use [Lombok](https://projectlombok.org/) to generate tons fo code automatically
- Use the Stream API to make your code more concise and readable, please...
- Stop null checking from DAOs, use ```Optional``` instead
- Use GitHub CoPilot to generate code for you
