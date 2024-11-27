CREATE VIEW tourist_attractions AS(
                                  SELECT content_id, title, first_image1, map_level, latitude, longitude,
                                         addr1,sido_name AS province, gugun_name AS city,
                                         a.content_type_id AS type_id
                                  FROM attractions AS a,contenttypes AS c,guguns AS g,sidos AS s
                                  WHERE a.content_type_id = c.content_type_id
                                    AND a.area_code = s.sido_code
                                    AND a.si_gun_gu_code = g.gugun_code
                                    AND s.sido_code = g.sido_code
                                  );