package org.billing.io;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardResponse {

    private Double todaySales;
    private Long todayOrderCount;
    private List<OrderResponse> recentOrders;

}
