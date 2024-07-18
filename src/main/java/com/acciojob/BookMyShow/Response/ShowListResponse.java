package com.acciojob.BookMyShow.Response;

import com.acciojob.BookMyShow.Models.Show;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShowListResponse {

    private List<Show> showList;
}
