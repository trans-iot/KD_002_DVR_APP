package tw.msigDvrBack.manual;

import java.util.List;

import tw.msigDvrBack.persistence.TbSysMod;

public interface TbSysModManualMapper {
    
    List<TbSysMod> selectToChkMod(TbSysMod record);
    
    List<TbSysMod> selectToChkChildMod(TbSysMod record);
    
    TbSysMod selectModName(TbSysMod record);
}