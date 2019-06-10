package com.cc.yhzt.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.yhzt.entity.Players;
import com.cc.yhzt.mapper.PlayersMapper;
import com.cc.yhzt.service.IPlayersService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author CC
 * @since 2019-05-16
 */
@Service
public class PlayersServiceImpl extends ServiceImpl<PlayersMapper, Players> implements IPlayersService {

}
