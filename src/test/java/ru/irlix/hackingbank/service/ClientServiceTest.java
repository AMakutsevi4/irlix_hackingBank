package ru.irlix.hackingbank.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.irlix.hackingbank.dto.ClientDTO;
import ru.irlix.hackingbank.model.Client;
import ru.irlix.hackingbank.repository.ClientRepository;

import java.util.List;

/*Аннотация указывает, что тестовый класс будет использовать расширение Mockito
 * Это расширение упраавляет жизненным циклом МОК объектов*/
@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    /*Используется для создания МОК объекта, используется для подмены вызовов к реальному репозиторию*/
    @Mock
    private ClientRepository clientRepository;

    /*Указывает, что Mockito должен создать экземпляр класса клиентСервис и внедрить в него все моки*/
    @InjectMocks
    ClientService clientService;

    /*Указывает ,что метод является тестовым и должен быть выполнен фреймворком JUnit*/
    @Test
    void getAllClients() {
        List<Client> clients = createClient();
        /*Настройка мок объекта (клиент репозиторий) таким образом,
        что при вызове метода findALL(), он возвращал заранее подготовленный список,
         т.е. имитирует поведение реального репозитория*/
        Mockito.when(clientRepository.findAll()).thenReturn(clients);

    /*Этот метод обращается к репозиторию и вызывает метод findAll(),
    затем преобразует список клиентов в DTO*/
        List<ClientDTO> result = (List<ClientDTO>) clientService.getAll();

        Assertions.assertEquals(clients.size(), result.size());
        Assertions.assertEquals(clients.get(0).getName(), result.get(0).getName());
        Assertions.assertEquals(clients.get(2).getName(), result.get(2).getName());
    }

    @Test
    void whenCreateClientThenTrue() {
        Client client = new Client(1L, "Alexandr", "8914225225", 1500.0);

        /*doNothing указывает что метод save не должен ничего возвращать*/
        /*Mockito.doNothing().when(clientRepository.save(client));*/

        clientService.save(client);

        /*В данном случае, мы проверяем что через мок объект(clientService)
         метод save был вызван 1 раз метод save*/
        Mockito.verify(clientRepository, Mockito.times(1)).save(client);
    }

    @Test
    void whenDeleteClientThenTrue() {
        Client client = new Client(1L, "Alexandr", "8914225225", 1500.0);

        /*doNothing указывает что метод save не должен ничего возвращать*/
        /*Mockito.doNothing().when(clientRepository).delete(client);*/

        clientService.delete(client);

        /*В данном случае, мы проверяем что через мок объект(clientService)
         метод save был вызван 1 раз метод delete */
        Mockito.verify(clientRepository, Mockito.times(1)).delete(client);
    }

    private List<Client> createClient() {
        Client firstClient = new Client(1L, "Alexandr", "8914225225", 1500.0);
        Client secondClient = new Client(2L, "Petr", "8800200060", 1000.0);
        Client threeClient = new Client(3L, "Vasya", "884540", 500.0);

        return List.of(firstClient, secondClient, threeClient);
    }
}