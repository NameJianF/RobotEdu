package live.itrip.admin.service.impls;

import live.itrip.admin.dao.AdminProvinceMapper;
import live.itrip.admin.model.AdminProvince;
import live.itrip.admin.service.BaseService;
import live.itrip.admin.service.intefaces.IAdminProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Feng on 2017/8/10.
 */
@Service
public class AdminProvinceService extends BaseService implements IAdminProvinceService {
    @Autowired
    private AdminProvinceMapper adminProvinceMapper;


    @Override
    public List<AdminProvince> selectAll() {
        return adminProvinceMapper.selectAll();
    }
}
