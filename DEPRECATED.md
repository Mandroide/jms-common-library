# Deprecation Notice

| Item | Details                                                                                                                 |
|------|-------------------------------------------------------------------------------------------------------------------------|
| **Decision date** | 03 Jul 2025                                                                                                             |
| **Decision makers** | PO: Jane Doe · Tech Lead: John Doe                                                                                      |
| **Reference** | JIRA-1234 – “Migration no longer necessary” (see comment dated 02 Jul 2025)                                             |
| **Reason** | The target platform now natively supports the required feature; separate migration effort provides no additional value. |
| **Superseded by** | [`new-service-xyz`](https://git.example.com/org/new-service-xyz)                                                        |
| **Support window** | _None_ – repository is immediately frozen.                                                                              |
| **Archive request** | Repo owner to mark **Archived / Read-only** after this PR merges.                                                       |

## 1. Impact
* Builds, CI jobs and downstream deployments **must stop consuming this artifact**.
* Pipelines referencing `old-service` should switch to `new-service-xyz` tag ≥ v2.0.0.

## 2. Migration guide (if any)
_No code migration is necessary. Delete old references and import the new module._

## 3. Contact
* Slack: `#team-integration`
* Email: `integration@example.com`