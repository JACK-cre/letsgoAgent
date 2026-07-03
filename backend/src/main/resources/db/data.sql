INSERT INTO users (id, username, nickname, language, timezone)
VALUES (1, 'demo', 'Demo User', 'zh-CN', 'Asia/Shanghai')
ON DUPLICATE KEY UPDATE nickname = VALUES(nickname);

INSERT INTO user_preference (user_id, preference_key, preference_value, source, confidence)
VALUES
  (1, 'answer_style', '清晰、直接、带一点专业建议', 'seed', 1.00),
  (1, 'language', '中文', 'seed', 1.00)
ON DUPLICATE KEY UPDATE preference_value = VALUES(preference_value);

INSERT INTO skill_definition (skill_code, skill_name, description, enabled, required_context)
VALUES
  ('profile', '用户画像 Skill', '根据用户资料和偏好调整回答方式。', TRUE, 'user,preferences'),
  ('memory', '记忆管理 Skill', '处理记住、更新、删除用户偏好的请求。', TRUE, 'user,preferences,memories'),
  ('recommendation', '个性化推荐 Skill', '根据用户偏好、记忆和当前上下文进行推荐。', TRUE, 'user,preferences,memories'),
  ('task', '任务规划 Skill', '把目标拆成可执行计划和步骤。', TRUE, 'recentMessages'),
  ('knowledge', '知识问答 Skill', '面向一般知识、业务知识和文档知识的问答。', TRUE, 'recentMessages'),
  ('fallback', '兜底 Skill', '当意图不明确时给出稳妥回答或追问。', TRUE, '')
ON DUPLICATE KEY UPDATE skill_name = VALUES(skill_name), description = VALUES(description), enabled = VALUES(enabled);

