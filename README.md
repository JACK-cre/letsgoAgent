# letsgoAgent

agent从0开始

一个基于 Spring Boot、Spring AI、MySQL、Redis、MyBatis 的多用户个性化助手 Agent 项目。

项目采用前后端分离结构：

- `backend/`: Spring Boot 后端服务，负责用户画像、记忆、Skill 路由、模型调用、日志审计。
- `frontend/`: 前端应用，负责聊天、用户画像、记忆管理、Skill 查看。
- `deploy/`: 本地 Docker Compose 与云部署参考配置。
- `docs/`: 架构、部署和扩展说明。

## 核心能力

- 多用户访问
- MySQL 长期保存用户资料、偏好、记忆和日志
- Redis 保存短期会话上下文
- 插件式 Skill 机制
- Spring AI 模型接入预留
- 本地 Docker 一键启动 MySQL、Redis、后端、前端
- 可迁移到云平台部署

## 本地启动

后端需要 JDK 17 或更高版本。当前项目默认可以在没有模型 API Key 的情况下运行，会使用本地模拟 AI 回复。

```bash
cd personal-agent-assistant
docker compose -f deploy/docker-compose.yml up --build
```

打开：

- 前端: http://localhost:5173
- 后端健康检查: http://localhost:8080/api/health

## 云部署思路

生产环境推荐：

- 后端部署为 Docker 容器
- 前端部署为静态站点或 Nginx 容器
- MySQL 使用云数据库
- Redis 使用云缓存
- 模型 API Key 通过云平台环境变量注入

详细说明见 [docs/deployment.md](docs/deployment.md)。

## 不使用 Docker 的本机开发

如果你的默认 Java 不是 JDK 17，可以临时指定：

```bash
cd personal-agent-assistant/backend
JAVA_HOME=/Library/Java/JavaVirtualMachines/temurin-17.jdk/Contents/Home mvn spring-boot:run
```

前端：

```bash
cd personal-agent-assistant/frontend
npm install
npm run dev
```
