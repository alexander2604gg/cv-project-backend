package com.alexander.cv_project.auth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.alexander.cv_project.auth.entity.RolePermissionEntity;
import com.alexander.cv_project.auth.entity.RolePermissionId;

public interface RolePermissionRepository extends JpaRepository<RolePermissionEntity, RolePermissionId> {
    @Query("SELECT rp FROM RolePermissionEntity rp WHERE rp.id.roleId = :roleId")
    List<RolePermissionEntity> findByRoleId(@Param("roleId") Long roleId);

    @Query("SELECT rp FROM RolePermissionEntity rp WHERE rp.id.permissionId = :permissionId")
    List<RolePermissionEntity> findByPermissionId(@Param("permissionId") Long permissionId);

    @Modifying
    @Query("DELETE FROM RolePermissionEntity rp WHERE rp.id.roleId = :roleId")
    void deleteAllByRoleId(@Param("roleId") Long roleId);

    @Modifying
    @Query("""
    DELETE FROM RolePermissionEntity rp
    WHERE rp.role.id = :roleId 
    AND rp.permission.id IN :permissionIds
""")
    void deleteByRoleIdAndPermissionIds(Long roleId, List<Long> permissionIds);
}
