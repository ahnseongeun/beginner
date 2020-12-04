package com.inflearnbeginner.beginner.repository;

import com.inflearnbeginner.beginner.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

//실무에서도 많이 사용된다.
public class JdbcTemplateMemberRepository implements MemberRepository{

    private final JdbcTemplate jdbcTemplate;

    @Autowired //생성자가 하나일때 Autowired 생략가능
    public JdbcTemplateMemberRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public Member save(Member member) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("member").usingGeneratedKeyColumns("id");
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", member.getName());
        Number key = jdbcInsert.executeAndReturnKey(new
                MapSqlParameterSource(parameters));
        member.setId(key.longValue());
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        List<Member> list= jdbcTemplate.query("select * from member where id =?",memberRowMapper(),id);
        return list.stream().findAny();
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> list= jdbcTemplate.query("select * from member where name =?",memberRowMapper(),name);
        return list.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        List<Member> list= jdbcTemplate.query("select * from member ",memberRowMapper());
        return list;
    }

    private RowMapper<Member> memberRowMapper(){
        return new RowMapper<Member>() {
            @Override
            public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
                Member member=new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                return member;
            }
        };

//        return (rs, rowNum) -> {
//            Member member=new Member();
//            member.setId(rs.getLong("id"));
//            member.setName(rs.getString("name"));
//            return member;
//        }
    }
}
