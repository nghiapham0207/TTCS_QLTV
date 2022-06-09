use QLTV
go
create proc sp_newLogin
@loginame nvarchar(128),
@pwd sysname = Null,
@defdb sysname = 'master',
@policy bit = 0,
@exp bit = 0,
@must_change bit = 0
as
begin
	declare @exec_stmt nvarchar(4000)
	if @pwd is null
		select @pwd = ''

	set @exec_stmt = 'create login ' + quotename(@loginame) + ' with password ='+ quotename(@pwd, '''')

	if @must_change = 1
		set @exec_stmt = @exec_stmt + ' must_change'

	set @exec_stmt = @exec_stmt + ', default_database=' + quotename(@defdb) 

	if @policy = 1
		set @exec_stmt = @exec_stmt + ', check_policy = on' 
	else
		set @exec_stmt = @exec_stmt + ', check_policy = off'

	if @exp = 1
		set @exec_stmt = @exec_stmt + ', check_expiration = on' 
	else
		set @exec_stmt = @exec_stmt + ', check_expiration = off'

	exec (@exec_stmt) --ok

	set @exec_stmt = ''

	set @exec_stmt = 'create user '+quotename(@loginame) + ' for login '+quotename(@loginame)

	exec (@exec_stmt)
	--exec sp_grantdbaccess @loginame, @loginame
end

go
create proc sp_removeLogin
@loginame sysname,
@name_in_db sysname
as
begin
	exec sp_dropuser @name_in_db

	exec sp_droplogin @loginame
end

go
create proc sp_getlistmember
@role_name sysname
as
begin
	select m.name as [Role Members] from sys.database_role_members rm 
	inner join sys.database_principals r on rm.role_principal_id = r.principal_id
	inner join sys.database_principals m on rm.member_principal_id = m.principal_id
	where r.name = @role_name
end

--create view
go
create view v_membertoadd
as
select name, 'User' as [type] from sys.sysusers where issqlrole = 0 and uid not in (1, 3, 4)
union
select name, 'Database Role' as [type] from sys.database_principals where is_fixed_role = 0 and type = 'R'

--select * from v_membertoadd

--create proc backup
go
create proc sp_backupdb_full
@dbname nvarchar(128),
@path nvarchar(128) 
as
begin
	declare @exec_stmt nvarchar(4000)
	set @exec_stmt = 'backup database ' + quotename(@dbname) + 
	' to disk = ' + quotename(@path, '''')
	exec (@exec_stmt)
end

go
create proc sp_backupdb_diff
@dbname nvarchar(128),
@path nvarchar(128)
as
begin
	declare @exec_stmt nvarchar(4000)
	set @exec_stmt = 'backup database ' + quotename(@dbname) + 
	' to disk = ' + quotename(@path, '''') + ' with differential'
	exec (@exec_stmt)
end

go
create proc sp_backuplog
@dbname nvarchar(128),
@path nvarchar(128)
as
begin
	declare @exec_stmt nvarchar(4000)
	set @exec_stmt = 'backup log ' + quotename(@dbname) + 
	' to disk = ' + quotename(@path, '''')
	exec (@exec_stmt)
end

go
create proc sp_backupsets
@dbname nvarchar(128),
@physical_device_name nvarchar(128)
as
begin
select case [type]
		when 'D' then 'Full'
		when 'I' then 'Differential'
		when 'L' then 'Transaction Log'
		else [type]
	end 
as backuptype, physical_device_name, database_name, position, backup_start_date, backup_finish_date
from msdb.dbo.backupset
INNER JOIN msdb.dbo.backupmediafamily ON backupset.media_set_id = backupmediafamily.media_set_id
where 
database_name = @dbname
--physical_device_name = @physical_device_name
and backup_finish_date >= (select top 1 backup_finish_date
                             from msdb.dbo.backupset b1
                             where b1.database_name = @dbname AND
                               b1.type = 'D'
                             order by b1.backup_finish_date desc)
order by [type], backup_finish_date
end