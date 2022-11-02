package com.godeltech.gbf.service.offer;

import com.godeltech.gbf.model.Role;
import com.godeltech.gbf.model.SessionData;
import com.godeltech.gbf.model.db.Offer;
import org.springframework.data.domain.Page;

import java.time.LocalDate;

public interface OfferService {
    void save(SessionData sessionData);

    void deleteExpiredOffersByDate(LocalDate expiredDate);
    // Поиск офферов по id юзера и по роли (нужна история офферов клиента или курьера)
    Page<Offer> findAllOffersByUserIdAndRole(Long userId, Role role, int pageNumber);

    // Удаляем оффер по его id
    void deleteOfferById(Long offerId);

    //основной метод поиска. Ищем по переданной сессии, роли может быть две: курьер или клиент. Для этих двух ролей надо по разному комбинировать спецификации. Вначале ищем по маршруту (через RouteService), находим пересечения, вытягиваем айдишники офферов нужных нам.
    // Потом дополнительно парсим этот лист маршрутов дабы входило в друг друга и уже в этих айдишниках офферов ищем все остальное.
    Page<Offer> findAllOffersBySessionDataAndRole(SessionData sessionData, Role client, int pageNumber);
}
