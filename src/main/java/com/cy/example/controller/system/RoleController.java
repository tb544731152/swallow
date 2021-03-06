package com.cy.example.controller.system;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.cy.example.controller.BaseController;
import com.cy.example.entity.system.SysRoleEntity;
import com.cy.example.model.Page;
import com.cy.example.model.Result;
import com.cy.example.service.IRoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/system/role")
public class RoleController extends BaseController {

	@Autowired
	private IRoleService roleService;
	
	@PostMapping
	@RequiresPermissions("role_add")
	public Result<String> add(@ModelAttribute("role") SysRoleEntity role) {
		boolean flag = roleService.insert(role);
		return new Result<>(flag,flag?"添加成功！":"添加失败！",0,null);
	}

	@PutMapping
	@RequiresPermissions("role_update")
	public Result<String> update(@ModelAttribute("role") SysRoleEntity role) {
		boolean flag = roleService.updateById(role);
		return new Result<>(flag,flag?"更新成功！":"更新失败！",0,null);
	}

	@DeleteMapping("/{id}")
	@RequiresPermissions("role_delete")
	public Result<String> delete(@PathVariable("id")Long id) {
		boolean flag = roleService.deleteById(id);
		return new Result<>(flag,flag?"删除成功！":"删除失败！",0,null);
	}

	@GetMapping
	@RequiresPermissions("role_list")
	public Result<List<SysRoleEntity>> findAll(int page, int rows) {
		com.baomidou.mybatisplus.plugins.Page list = roleService.selectPage(new com.baomidou.mybatisplus.plugins.Page(page, rows)
				, new EntityWrapper<SysRoleEntity>().setSqlSelect("c_roleCode,c_roleName,c_createDate,c_updateDate,id"));
		Map<String, Object> map = new HashMap<String, Object>();
		int sum = roleService.selectCount(new EntityWrapper<SysRoleEntity>());
		return new Result<>(true,null,sum,list.getRecords());
	}

	@GetMapping("/search")
	@RequiresPermissions("role_list")
	public Result<List<SysRoleEntity>> search(
			@ModelAttribute("role") SysRoleEntity role,
			@ModelAttribute("page") Page page) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<SysRoleEntity> list = roleService.searchAll(
				role, page);
		int sum = roleService.searchAllCount(role);
		return new Result<>(true,null,sum,list);
	}

}
