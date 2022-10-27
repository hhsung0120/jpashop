package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Order;
import jpabook.jpashop.repository.order.simplequery.OrderSimpleQueryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    public void save(Order order) {
        em.persist(order);
    }

    public Order findOne(Long id) {
        return em.find(Order.class, id);
    }

    public List<Order> findAll(OrderSearch orderSearch) {

        if (orderSearch.getMemberName() == null && orderSearch.getOrderStatus() != null) {
            return em.createQuery("select o from Order o join o.member m" +
                            " where o.status = :status ", Order.class)
                    .setParameter("status", orderSearch.getOrderStatus())
                    .setMaxResults(1000)
                    .getResultList();
        }

        if (orderSearch.getMemberName() != null && orderSearch.getOrderStatus() == null) {
            return em.createQuery("select o from Order o join o.member m" +
                            " where m.name like :name ", Order.class)
                    .setParameter("name", orderSearch.getMemberName())
                    .setMaxResults(1000)
                    .getResultList();
        }

        if (orderSearch.getMemberName() != null && orderSearch.getOrderStatus() != null) {
            return em.createQuery("select o from Order o join o.member m" +
                            " where o.status = :status " +
                            " and m.name like :name", Order.class)
                    .setParameter("status", orderSearch.getOrderStatus())
                    .setParameter("name", orderSearch.getMemberName())
                    .setMaxResults(1000)
                    .getResultList();
        }

        return em.createQuery("select o from Order o join o.member m", Order.class)
                .setMaxResults(1000)
                .getResultList();
    }

    public List<Order> findAllWithMemberDelivery() {
        List<Order> resultList =
                em.createQuery("select o from Order o" +
                        " join fetch o.member m" +
                        " join fetch o.delivery d", Order.class
                ).getResultList();

        return resultList;
    }

    public List<OrderSimpleQueryDto> findOrderDtos() {
        List<OrderSimpleQueryDto> resultList =
                em.createQuery("select new jpabook.jpashop.repository.order.simplequery.OrderSimpleQueryDto(o.id, m.name, o.orderDate, o.status, d.address) " +
                                        " from Order o" +
                                        " join o.member m" +
                                        " join o.delivery d", OrderSimpleQueryDto.class
        ).getResultList();

        return resultList;
    }
}
