USE `testattendanceapp`;
DROP procedure IF EXISTS `delete_selected_schools`;

DELIMITER $$
USE `testattendanceapp`$$
PROCEDURE `delete_selected_schools`(comma_seperated_ids_set VARCHAR(2048))
BEGIN
	SET @userids = (SELECT GROUP_CONCAT(id) FROM users WHERE FIND_IN_SET(school_id,comma_seperated_ids_set));
	DELETE FROM authorities WHERE FIND_IN_SET(user_id, @userids);
	DELETE FROM users WHERE FIND_IN_SET(id, @userids);
	DELETE FROM students WHERE FIND_IN_SET(school_id,comma_seperated_ids_set);
	DELETE FROM schools WHERE FIND_IN_SET(id,comma_seperated_ids_set);
END$$

DELIMITER ;

